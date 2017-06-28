DESCRIPTION = "u-boot bootloader for the RaspberryPi2"

LICENSE = "gpl-2.0"
LIC_FILES_CHKSUM = "file://${LAYERDIR_isar}/licenses/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

PV = "2016.11+dfsg1-4"

SRC_URI = "file://u-boot-rpi-2016.11.tar.gz"

SRC_DIR = "u-boot-2016.11"

inherit dpkg
