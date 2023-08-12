output "alb_sg_id" {
  value       = aws_security_group.alb.id
  description = "A security group id of a load balancer"
}

output "backend_security_group_id" {
  value       = aws_security_group.backend.id
  description = "A backend security group id"
}

output "frontend_security_group_id" {
  value       = aws_security_group.frontend.id
  description = "A frontend security group id"
}

#output "ec2_connect_endpoint_sg_id" {
#  value       = aws_security_group.ec2_connect_endpoint.id
#  description = "A security group id of a EC2"
#}

output "rds_sg_id" {
  value       = aws_security_group.database.id
  description = "A security group id of a RDS database"
}