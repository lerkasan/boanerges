output "deployment_group_arn" {
  value       = aws_codedeploy_deployment_group.this.arn
  description = "ARN of a CodeDeploy deployment group"
}
