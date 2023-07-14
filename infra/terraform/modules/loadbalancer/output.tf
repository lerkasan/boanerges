output "dns_name" {
  value       = aws_lb.app.dns_name
  description = "A DNS name of a load balancer"
}

output "zone_id" {
  value       = aws_lb.app.zone_id
  description = "A zone id of a load balancer"
}

output "target_group_name" {
  value       = aws_lb_target_group.app.name
  description = "A target group name of a load balancer"
}
