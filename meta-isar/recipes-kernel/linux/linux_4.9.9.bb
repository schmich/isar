# Sample application
#
# This software is a part of ISAR.
# Copyright (C) 2015-2016 ilbers GmbH

DESCRIPTION = "Linux Kernel"

LICENSE = "gpl-2.0"
LIC_FILES_CHKSUM = "file://${LAYERDIR_isar}/licenses/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://localhost:8680/Debian/linux.git;protocol=http;branch=lenormf/linux-4.9.9-deb;user=lenormf:794f8ba14e04b12665c19ddc21e95979"
SRCREV = "b7ab449238b8e59943849a10c95d578aa01d70e7"

SRC_DIR = "git"

inherit dpkg
