output "ec2_instances_ids" {
  value       = [ for az in toset(local.availability_zones)  : aws_instance.appserver[az].id ]
  description = "EC2 instances ids"
}

output "iam_role_arn" {
  value       = aws_iam_role.appserver_iam_role.arn
  description = "IAM role ARN of the EC2 instances"
}
