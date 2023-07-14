output "ssm_param_db_host_arn" {
  value       = aws_ssm_parameter.database_host.arn
  description = "ARN of the SSM parameter for the database host"
}

output "ssm_param_db_name_arn" {
  value       = aws_ssm_parameter.database_name.arn
  description = "ARN of the SSM parameter for the database name"
}

output "ssm_param_db_username_arn" {
  value       = aws_ssm_parameter.database_username.arn
  description = "ARN of the SSM parameter for the database username"
}

output "ssm_param_db_password_arn" {
  value       = aws_ssm_parameter.database_password.arn
  description = "ARN of the SSM parameter for the database password"
}

output "kms_key_arn" {
  value       = aws_kms_key.ssm_param_encrypt_key.arn
  description = "ARN of the KMS key used to encrypt the SSM parameters"
}
