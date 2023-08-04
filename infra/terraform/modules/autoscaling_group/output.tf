#output "ec2_instances_ids" {
#  value       = [ for az in toset(local.availability_zones)  : aws_launch_template.appserver[az].id ]
#  description = "EC2 instances ids"
#}

output "iam_role_arn" {
  value       = aws_iam_role.appserver_iam_role.arn
  description = "IAM role ARN of the EC2 instances"
}

output "name" {
  value       = aws_autoscaling_group.appserver.name
  description = "Name of the autoscaling group"
}

#output "ami_id" {
#  value = aws_instance.appserver[local.availability_zones[0]].ami
#  description = "AMI id of the EC2 instances"
#}
#
#output "ec2_instance_type" {
#  value = aws_instance.appserver[local.availability_zones[0]].ami
#  description = "AMI id of the EC2 instances"
#}
#
#instance_type               = var.ec2_instance_type
#user_data                   = data.cloudinit_config.user_data.rendered
#key_name                    = var.appserver_private_ssh_key_name
#
#vpc_security_group_ids      = [ var.ec2_sg_id ]