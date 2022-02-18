# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel ??= "linux-raspberrypi"
PREFERRED_PROVIDER_jpeg ?= "jpeg"

PREFERRED_PROVIDER_virtual/libomxil ?= "userland"
VIRTUAL-RUNTIME_libomxil = "userland"

PREFERRED_PROVIDER_u-boot-default-script ??= "rpi-u-boot-scr"
