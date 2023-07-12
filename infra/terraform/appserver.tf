resource "aws_instance" "appserver" {
  for_each                    = toset(local.availability_zones)

  availability_zone           = each.value
  subnet_id                   = aws_subnet.private[each.value].id
  associate_public_ip_address = false
  ami                         = data.aws_ami.ubuntu.id
  instance_type               = var.ec2_instance_type
  user_data                   = data.cloudinit_config.user_data.rendered
  key_name                    = var.appserver_private_ssh_key_name
  vpc_security_group_ids      = [ aws_security_group.appserver.id ]
  monitoring                  = true
  iam_instance_profile        = aws_iam_instance_profile.this.name

  root_block_device {
    delete_on_termination = true
    volume_type           = "gp3"
    volume_size           = 10
  }

tags = {
    Name        = join("_", [var.project_name, "_appserver"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }

  # Dependency is used to ensure that EC2 instance will have Internet access during userdata execution to be able to install packages
  depends_on  = [ aws_internet_gateway.this, aws_nat_gateway.this ]
}

resource "aws_security_group" "appserver" {
  name        = join("_", [var.project_name, "_appserver_security_group"])
  description = "security group for application server"
  vpc_id      = aws_vpc.this.id

  tags = {
    Name        = join("_", [var.project_name, "_appserver_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_iam_instance_profile" "this" {
  name = join("_", [var.project_name, "_ec2_profile"])
  role = aws_iam_role.appserver_iam_role.name
}

resource "aws_iam_role" "appserver_iam_role" {
  name        = join("", [title(var.project_name), "AppserverRole"])
  description = "The role for EC2 instances for appserver"
  assume_role_policy = data.aws_iam_policy_document.assume_role_ec2.json
}

data "aws_iam_policy_document" "assume_role_ec2" {
  statement {
    sid           = "EC2AssumeRole"
    effect        = "Allow"
    actions       = [ "sts:AssumeRole" ]
    principals {
      type        = "Service"
      identifiers = [ "ec2.amazonaws.com" ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "AmazonSSMManagedInstanceCore" {
  role       = aws_iam_role.appserver_iam_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "GetDeploymentAndGetSSMParameter" {
  role       = aws_iam_role.appserver_iam_role.name
  policy_arn = aws_iam_policy.read_access_to_parameters_and_deployments.arn
}

resource "aws_iam_policy" "read_access_to_parameters_and_deployments" {
  name        = "read-access-to-parameters-and-deployments"
  description = "Allow to get deployment information to retrieve a commitId hash"
  policy      = data.aws_iam_policy_document.read_access_to_parameters_and_deployments.json
}

data "aws_iam_policy_document" "read_access_to_parameters_and_deployments" {
  statement {
    sid       = "CodeDeployGetDeployments"
    effect    = "Allow"
    actions   = [
      "codedeploy:GetDeployment",
      "codedeploy:ListDeployments"
    ]
    resources = [ aws_codedeploy_deployment_group.this.arn ]
  }

  statement {
    sid       = "SSMGetParameter"
    effect    = "Allow"
    actions   = [ "ssm:GetParameter" ]
    resources = [
      aws_ssm_parameter.database_host.arn,
      aws_ssm_parameter.database_name.arn,
      aws_ssm_parameter.database_username.arn,
      aws_ssm_parameter.database_password.arn
    ]
  }

  statement {
    sid       = "KMSDecrypt"
    effect    = "Allow"
    actions   = [
      "kms:Decrypt",
      "kms:DescribeKey"
    ]
    resources = [ aws_kms_key.ssm_param_encrypt_key.arn ]
  }

  statement {
    sid       = "CloudWatchLogs"
    effect    = "Allow"
    actions   = [
      "logs:CreateLogStream",
      "logs:PutLogEvents",
      "logs:CreateLogGroup"
    ]
    resources = [ "*" ]
  }
}

data "aws_ami" "amazon_linux2" {
  owners      = ["amazon"]
  most_recent = true

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-ebs"]
  }
}

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = [ local.ami_name ]
  }

  filter {
    name   = "architecture"
    values = [ local.ami_architecture ]
  }

  filter {
    name   = "virtualization-type"
    values = [ var.ami_virtualization ]
  }

  owners = [ local.ami_owner_id ]
}

data "aws_ssm_parameter" "admin_public_ssh_keys" {
  for_each = toset(var.admin_public_ssh_keys)

  name = each.value
  with_decryption = true
}

# ------------------- User data for cloud-init --------------------------
# The public ssh key will added to ec2 instances using cloud-init
data "cloudinit_config" "user_data" {
  gzip          = true
  base64_encode = true

  part {
    content_type = "text/cloud-config"
    content      = templatefile("templates/userdata.tftpl", {
      public_ssh_keys: [ for key in data.aws_ssm_parameter.admin_public_ssh_keys: key.value]
    })
  }
}

resource "aws_ec2_instance_connect_endpoint" "this" {
  subnet_id             = aws_subnet.private[local.availability_zones[0]].id
  security_group_ids    = [ aws_security_group.ec2_connect_endpoint.id ]
  preserve_client_ip    = true

  tags = {
    Name        = join("_", [var.project_name, "ec2_connect_endpoint"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

# According to AWS documentation, there are no quotas for ec2 instance connect endpoints, however recently it is not possible to create more than 1 ec2 instance connect endpoint
#resource "aws_ec2_instance_connect_endpoint" "this" {
#  for_each              = toset(local.availability_zones)
#
#  subnet_id             = aws_subnet.private[each.value].id
#  security_group_ids    = [ aws_security_group.ec2_connect_endpoint.id ]
#  preserve_client_ip    = true
#
#  tags = {
#    Name        = join("_", [var.project_name, "ec2_connect_endpoint"])
#    terraform   = "true"
#    environment = var.environment
#    project     = var.project_name
#  }
#}

resource "aws_security_group" "ec2_connect_endpoint" {
  name        = join("_", [var.project_name, "ec2_connect_endpoint_sg"])
  description = "security group for ec2 instance connect endpoint"
  vpc_id      = aws_vpc.this.id

  tags = {
    Name        = join("_", [var.project_name, "ec2_connect_endpoint_sg"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}

resource "aws_iam_policy" "connect_via_ec2_instance_connect_endpoint" {
  name        = "ec2-instance-connect"
  description = "Allow to connect to EC2 instance via EC2 Instance Connect Endpoint"
  policy      = data.aws_iam_policy_document.connect_to_ec2_via_ec2_instance_connect_endpoint.json
}

data "aws_iam_policy_document" "connect_to_ec2_via_ec2_instance_connect_endpoint" {
  #  dynamic "statement" {
  #    for_each = toset(local.availability_zones)

  statement {
    sid     = "EC2ConnectEndpoint"
    effect  = "Allow"
    actions = [
      "ec2-instance-connect:OpenTunnel"
    ]
    resources = [ aws_ec2_instance_connect_endpoint.this.arn ]
  #  resources = [for az in toset(local.availability_zones) : aws_ec2_instance_connect_endpoint.this[az].arn ]  # Because of the issue with creating more than 1 ec2 instance connect endpoint, we use only 1 ec2 instance connect endpoint

    condition {
      test     = "NumericEquals"
      variable = "ec2-instance-connect:remotePort"
      values   = [var.ssh_port]
    }

    condition {
      test     = "IpAddress"
      variable = "ec2-instance-connect:privateIpAddress"
      values   = [ for az in toset(local.availability_zones) : aws_instance.appserver[az].private_ip]
    }
  }

  statement {
    sid     = "EC2DescribeConnectEndpoints"
    effect  = "Allow"
    actions = [
      "ec2:DescribeInstances",
      "ec2:DescribeInstanceConnectEndpoints"
    ]
    resources = ["*"]
  }
}

resource "aws_cloudwatch_log_group" "nginx" {
  name = "/var/log/nginx"

  tags = {
    Name        = join("_", [var.project_name, "nginx_log_group"])
    terraform   = "true"
    environment = var.environment
    project     = var.project_name
  }
}