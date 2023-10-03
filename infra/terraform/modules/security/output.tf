output "alb_sg_id" {
  value       = aws_security_group.alb.id
  description = "A security group id of a load balancer"
}

output "ec2_sg_id" {
  value       = aws_security_group.appserver.id
  description = "A security group id of a EC2"
}

output "ec2_connect_endpoint_sg_id" {
  value       = aws_security_group.ec2_connect_endpoint.id
  description = "A security group id of a EC2"
}

output "rds_sg_id" {
  value       = aws_security_group.database.id
  description = "A security group id of a RDS database"
}