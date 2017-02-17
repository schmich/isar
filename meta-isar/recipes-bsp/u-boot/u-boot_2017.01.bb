DESCRIPTION = "Universal Boot Loader for embedded devices"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

SRC_URI = "git://localhost:4242/u-boot-debian"
SRCREV = "8ffd224b628ec0911af0b76cb45255e622a09992"

SRC_DIR = "git"

PR = "r0"

inherit dpkg
