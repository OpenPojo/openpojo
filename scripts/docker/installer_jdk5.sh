#!/usr/bin/expect

spawn "/jdk-1_5_0_22-linux-amd64.bin"
expect {
  -- --More-- {
    send " "
    exp_continue
  } "Do you agree" {
    send "yes\r"
    exp_continue
  } "Done." {
    exit
  }
}
