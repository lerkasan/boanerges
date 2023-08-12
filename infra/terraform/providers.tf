terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.12.0"
    }
  }
}

provider "aws" {
  region              = var.aws_region
  //allowed_account_ids = var.allowed_account_ids
}

