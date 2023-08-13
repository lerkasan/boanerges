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
    host_port             = var.container_port
    tmp_size_in_mb        = var.tmp_size_in_mb
    env_variables         = jsonencode(var.env_vars)
    secrets               = jsonencode(var.secrets)
    awslogs_group         = "boanerges__ecs_logs"
    awslogs_stream_prefix = "boanerges"
  }
}

resource "aws_ecs_task_definition" "this" {
  family                = var.task_name
  container_definitions = data.template_file.container_definition.rendered
  task_role_arn         = var.ecs_task_role_arn

  network_mode             = "awsvpc"
  cpu                      = var.container_cpu
  memory                   = var.container_memory
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = var.ecs_task_execution_role_arn
}

resource "aws_ecs_service" "this" {
  name            = var.service_name
  cluster         = var.cluster_id
  task_definition = aws_ecs_task_definition.this.arn
  launch_type     = "FARGATE"
  desired_count   = var.container_count

  health_check_grace_period_seconds = var.grace_period_in_seconds

  load_balancer {
    target_group_arn = var.target_group_arn
    container_name   = aws_ecs_task_definition.this.family
    container_port   = var.container_port
  }

  network_configuration {
    subnets          = var.private_subnets_ids
    security_groups  = var.security_group_ids
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
