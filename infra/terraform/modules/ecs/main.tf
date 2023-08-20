#resource "aws_iam_role" "backend_iam_role" {
#  name        = join("", [title(var.project_name), "BackendECSTaskRole"])
#  description = "The backend task role for ECS"
#  assume_role_policy = data.aws_iam_policy_document.assume_role_ecs.json
#}
#
#data "aws_iam_policy_document" "assume_role_ecs" {
#  statement {
#    sid           = "ECSAssumeRole"
#    effect        = "Allow"
#    actions       = [ "sts:AssumeRole" ]
#    principals {
#      type        = "Service"
#      identifiers = [ "ecs-tasks.amazonaws.com" ]
#    }
#  }
#}
#
#resource "aws_iam_role_policy_attachment" "DecryptAccessToKmsKey" {
#  role       = aws_iam_role.backend_iam_role.name
#  policy_arn = aws_iam_policy.decrypt_access_to_kms_key.arn
#}
#
#resource "aws_iam_policy" "decrypt_access_to_kms_key" {
#  name        = "read-access-to-parameters"
#  description = "Allow to get deployment information to retrieve a commitId hash"
#  policy      = data.aws_iam_policy_document.decrypt_access_to_kms_key.json
#}
#
#data "aws_iam_policy_document" "decrypt_access_to_kms_key" {
#
##  statement {
##    sid       = "SSMGetParameter"
##    effect    = "Allow"
##    actions   = [ "ssm:GetParameter" ]
##    resources = [
##      var.ssm_param_db_host_arn,
##      var.ssm_param_db_name_arn,
##      var.ssm_param_db_username_arn,
##      var.ssm_param_db_password_arn
##    ]
##  }
#
#  statement {
#    sid       = "KMSDecrypt"
#    effect    = "Allow"
#    actions   = [
#      "kms:Decrypt",
#      "kms:DescribeKey"
#    ]
#    resources = [ var.kms_key_arn ]
#  }
#
##  statement {
##    sid       = "CloudWatchLogs"
##    effect    = "Allow"
##    actions   = [
##      "logs:CreateLogStream",
##      "logs:PutLogEvents",
##      "logs:CreateLogGroup"
##    ]
##    resources = [ "*" ]
##  }
#}



data "template_file" "container_definition" {
  template = file("${path.module}/container-definition.json.tftpl")

  vars = {
    aws_region            = var.aws_region
    container_name        = var.task_name
    #    container_image       = "${var.aws_account}.dkr.ecr.${var.aws_region}.amazonaws.com/${var.project_name}-backend:latest"
#    container_image       = aws_ecr_repository.boanerges.repository_url/"${var.project_name}-backend:latest"
    container_image       = var.container_image
    container_port        = var.container_port
    container_cpu        = var.container_cpu
    host_port             = var.container_port
    capabilities          = jsonencode(var.capabilities)
    tmpfs                 = jsonencode(var.tmpfs)
    env_variables         = jsonencode(var.env_vars)
    secrets               = jsonencode(var.secrets)
    volume_mounts         = jsonencode(
      [ for volume in var.volumes:
        {
          containerPath = volume.containerPath,
          sourceVolume  = volume.name
        }
      ]
    )
    awslogs_group         = "boanerges__ecs_logs"
    awslogs_stream_prefix = "boanerges"
  }
}

#"mountPoints": [
#  {
#    "containerPath": "/etc/nginx/conf.d",
#    "sourceVolume": "nginx-conf"
#  }
#],

resource "aws_ecs_task_definition" "this" {
  family                = var.task_name
  container_definitions = data.template_file.container_definition.rendered

#  volume {
#    name      = "nginx-conf"
#    host_path = "nginx-conf"
#  }

  dynamic "volume" {
    for_each = var.volumes

    content {
      name      = volume.value.name
      host_path = volume.value.hostPath
    }
  }

  task_role_arn         = var.ecs_task_role_arn

  network_mode             = "awsvpc"  # compatible with target_type="ip" of aws_lb_target_group resource. awsvpc mode needs awsvpctrunking to increase max limit of ENI (elastic network interfaces) per ec2 instance to avoid RESOURCE:ENI error. awsvpctrunking is available only for some ec2 instance types. https://docs.aws.amazon.com/AmazonECS/latest/developerguide/container-instance-eni.html
#  network_mode             = "bridge"   # compatible with target_type="instance" of aws_lb_target_group resource. bridge mode might cause "host port in use" issues when ecs tries to start several same tasks (containers)
  cpu                      = var.container_cpu
  memory                   = var.container_memory
  requires_compatibilities = ["EC2"]
  execution_role_arn       = var.ecs_task_execution_role_arn
}

resource "aws_ecs_service" "this" {
  name            = var.service_name
  cluster         = var.cluster_id
  task_definition = aws_ecs_task_definition.this.arn
  launch_type     = "EC2"
  desired_count   = var.container_count

  health_check_grace_period_seconds = var.grace_period_in_seconds

  load_balancer {
    target_group_arn = var.target_group_arn
    container_name   = aws_ecs_task_definition.this.family
    container_port   = var.container_port
  }

  network_configuration {   # required for task definitions that use the awsvpc network mode, and not supported for other network modes
    subnets          = var.private_subnets_ids
    security_groups  = var.security_group_ids
  }


  ## Spread tasks evenly accross all Availability Zones for High Availability
  ordered_placement_strategy {
    type  = "spread"
    field = "attribute:ecs.availability-zone"
  }

  ## Make use of all available space on the Container Instances
  ordered_placement_strategy {
    type  = "binpack"
    field = "memory"
  }

#  log_configuration {
#    log_driver = "awslogs"
#    options    = {
#      awslogs-region  = var.aws_region
#      awslogs-group   = var.awslogs_group
#      tag             = "{{ with split .ImageName \":\" }}{{join . \"_\"}}{{end}}/{{.Name}}/{{.ID}}"
#    }
#  }

#  "logConfiguration": {
#    "logDriver": "awslogs",
#    "options": {
#      "awslogs-region" : "${aws_region}",
#      "awslogs-group" : "${awslogs_group}",
#      "awslogs-stream-prefix" : "${awslogs_stream_prefix}",
#      "tag": "{{ with split .ImageName \":\" }}{{join . \"_\"}}{{end}}/{{.Name}}/{{.ID}}"
#    }

  # Optional: Allow external changes without Terraform plan difference
  lifecycle {
    ignore_changes = [ desired_count ]
  }

  tags = {
    Name        = join("_", [var.project_name, "_ecs_service"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_ecs_capacity_provider" "this" {
  name  = "${var.project_name}_ECS_CapacityProvider_${var.environment}"

  auto_scaling_group_provider {
    auto_scaling_group_arn         = var.auto_scaling_group_arn
    managed_termination_protection = "ENABLED"   # To enable managed termination protection for a capacity provider, the Auto Scaling group must have instance protection from scale in enabled

    managed_scaling {
      maximum_scaling_step_size = 2
      minimum_scaling_step_size = 1
      status                    = "ENABLED"
      target_capacity           = 100
    }

  }
}

resource "aws_ecs_cluster_capacity_providers" "this" {
  cluster_name       = var.cluster_name
  capacity_providers = [aws_ecs_capacity_provider.this.name]
}

resource "aws_appautoscaling_target" "ecs_target" {
  max_capacity       = 4
  min_capacity       = 2
  resource_id        = "service/${var.cluster_name}/${aws_ecs_service.this.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "ecs_cpu_policy" {
  name               = "${var.project_name}_CPUTargetTrackingScaling_${var.environment}"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  target_tracking_scaling_policy_configuration {
    target_value = 85
    scale_in_cooldown  = 300
    scale_out_cooldown = 300

    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageCPUUtilization"
    }
  }
}

resource "aws_appautoscaling_policy" "ecs_memory_policy" {
  name               = "${var.project_name}_MemoryTargetTrackingScaling_${var.environment}"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.ecs_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_target.service_namespace

  target_tracking_scaling_policy_configuration {
    target_value = 85
    scale_in_cooldown  = 300
    scale_out_cooldown = 300

    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageMemoryUtilization"
    }
  }
}