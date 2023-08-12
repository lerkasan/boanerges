output "dns_name" {
  value       = aws_lb.app.dns_name
  description = "A DNS name of a load balancer"
}

output "zone_id" {
  value       = aws_lb.app.zone_id
  description = "A zone id of a load balancer"
}

output "backend_target_group_name" {
  value       = aws_lb_target_group.backend.name
  description = "A backend target group name of a load balancer"
}

output "backend_target_group_arn" {
  value       = aws_lb_target_group.backend.arn
  description = "A backend target group arn of a load balancer"
}

output "frontend_target_group_name" {
  value       = aws_lb_target_group.frontend.name
  description = "A frontend target group name of a load balancer"
}

output "frontend_target_group_arn" {
  value       = aws_lb_target_group.frontend.arn
  description = "A frontend target group arn of a load balancer"
}

