#output "private_subnets_ids" {
#  value = map(
#    local.availability_zones[0], aws_subnet.private[local.availability_zones[0]].id
#    local.availability_zones[1], aws_subnet.private[local.availability_zones[1]].id
#  )
#}

#output "public_subnets_ids" {
#  value = map(
#    local.availability_zones[0], aws_subnet.public[local.availability_zones[0]].id
#    local.availability_zones[1], aws_subnet.public[local.availability_zones[1]].id
#  )
#}

output "private_subnets_ids" {
  value       = [ for az in toset(local.availability_zones) : aws_subnet.private[az].id]
}

output "public_subnets_ids" {
  value       = [ for az in toset(local.availability_zones) : aws_subnet.public[az].id]
}

output "vpc_id" {
  value       = aws_vpc.this.id
  description = "VPC id"
}

output "internet_gateway_id" {
  value       = aws_internet_gateway.this.id
  description = "Internet gateway id"
}

output "nat_gateway_ids" {
  value       = [ for az in toset(local.availability_zones)  : aws_nat_gateway.this[az].id ]
  description = "NAT gateway ids"
}