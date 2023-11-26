resource "aws_codedeploy_app" "this" {
  name = var.project_name

  tags = {
    Name        = join("_", [var.project_name, "_appserver"])
    terraform   = "true"
    project     = var.project_name
  }
}

data "aws_iam_policy_document" "assume_role_codedeploy" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["codedeploy.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "codedeploy" {
  name               = "codedeployRole"
  assume_role_policy = data.aws_iam_policy_document.assume_role_codedeploy.json
}

resource "aws_iam_role_policy_attachment" "AWSCodeDeployRole" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole"
  role       = aws_iam_role.codedeploy.name
}

resource "aws_codedeploy_deployment_group" "this" {
  app_name                = aws_codedeploy_app.this.name
  deployment_group_name   = "production"
  autoscaling_groups      = [var.autoscaling_group_name]
  service_role_arn        = aws_iam_role.codedeploy.arn
  deployment_config_name  = "CodeDeployDefault.OneAtATime"
  #  deployment_config_name = aws_codedeploy_deployment_config.this.id

  deployment_style {
    deployment_option = "WITH_TRAFFIC_CONTROL"
    deployment_type   = "IN_PLACE"
    #    deployment_type   = "BLUE_GREEN"
  }

  load_balancer_info {
    target_group_info {
      name = var.target_group_name
    }
  }

  auto_rollback_configuration {
    enabled = true
    events  = ["DEPLOYMENT_FAILURE"]
  }

  ec2_tag_set {
    ec2_tag_filter {
      key   = "Name"
      type  = "KEY_AND_VALUE"
      value = var.project_name
    }

    ec2_tag_filter {
      key   = "project"
      type  = "KEY_AND_VALUE"
      value = var.project_name
    }
  }

  tags = {
    Name        = join("_", [var.project_name, "_appserver"])
    terraform   = "true"
    project     = var.project_name
  }
}
