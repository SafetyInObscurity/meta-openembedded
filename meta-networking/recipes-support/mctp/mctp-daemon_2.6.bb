require mctp.inc

SUMMARY = "A daemon implementing the MCTP control protocol"

inherit systemd useradd

do_install:append () {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/conf/mctpd.service \
            ${D}${systemd_system_unitdir}/mctpd.service
    install -m 0644 ${S}/conf/*.target \
            ${D}${systemd_system_unitdir}/
    install -d ${D}${datadir}/dbus-1/system.d
    install -m 0644 ${S}/conf/mctpd-dbus.conf \
            ${D}${datadir}/dbus-1/system.d/mctpd.conf

    rm -r ${D}${bindir}
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "mctpd.service mctp.target mctp-local.target"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -U -s /bin/false mctpd"

DEPENDS = "systemd"
RDEPENDS:${PN} = "libsystemd"

FILES:${PN} = "${datadir}/dbus-1/system.d/mctpd.conf ${sbindir}/mctpd"
