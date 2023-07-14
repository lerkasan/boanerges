terraform {
  backend "s3" {
    region         = "us-east-1"
    bucket         = "boanerges-terraform-state"
    key            = "demo/terraform.tfstate"
    encrypt        = true
    acl            = "private"
  }
}