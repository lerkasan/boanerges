resource "aws_lb" "app" {
  name               = "demo-webapp-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [ var.alb_sg_id ]
  subnets            = var.public_subnets_ids
#  subnets            = [ for subnet in aws_subnet.public : subnet.id ]
  //enable_deletion_protection = true

  tags = {
    Name        = join("_", [var.project_name, "_app_alb"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_lb_target_group" "app" {
  name     = join("-", [var.project_name, "-app-tg"])
  port     = local.http_port
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  deregistration_delay = 30

  health_check {
    healthy_threshold   = 5
    interval            = 60
    matcher              = "200"
    path                = "/"
    protocol            = "HTTP"
    timeout             = 10
    unhealthy_threshold = 4
  }

  stickiness {
    type            = "lb_cookie"
    cookie_duration = 86400  // 1 day in seconds
  }

  tags = {
    Name        = join("_", [var.project_name, "_app_tg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_lb_target_group_attachment" "app" {
  for_each         = toset(local.availability_zones)

  target_group_arn = aws_lb_target_group.app.arn
  target_id        = var.ec2_instances_ids[index(local.availability_zones, each.value)]
#  target_id        = aws_instance.appserver[each.value].id
  port             = local.http_port
}

resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.app.arn
  port              = local.http_port
  protocol          = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port        = local.https_port
      protocol    = "HTTPS"
      status_code = "HTTP_301"
    }
  }

  tags = {
    Name        = join("_", [var.project_name, "_app_lb_listener"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_lb_listener" "https" {
  load_balancer_arn = aws_lb.app.arn
  port              = local.https_port
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = data.aws_acm_certificate.lerkasan_net.arn

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app.arn
  }

  tags = {
    Name        = join("_", [var.project_name, "_app_lb_listener"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

data "aws_acm_certificate" "lerkasan_net" {
  domain      = "lerkasan.net"
  statuses    = ["ISSUED"]
  types       = ["AMAZON_ISSUED"]
  key_types   = ["EC_prime256v1"]
  most_recent = true
}

locals {
  http_port = 80
  https_port = 443
  availability_zones = [for az_letter in var.az_letters : format("%s%s", var.aws_region, az_letter)]
}
