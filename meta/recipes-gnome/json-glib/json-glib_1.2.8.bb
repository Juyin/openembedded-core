SUMMARY = "JSON-GLib implements a full JSON parser using GLib and GObject"
DESCRIPTION = "Use JSON-GLib it is possible to parse and generate valid JSON\
 data structures, using a DOM-like API. JSON-GLib also offers GObject \
integration, providing the ability to serialize and deserialize GObject \
instances to and from JSON data types."
HOMEPAGE = "http://live.gnome.org/JsonGlib"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=7fbc338309ac38fefcd64b04bb903e34"

DEPENDS = "glib-2.0"

SRC_URI_append = " \
           file://0001-Do-not-disable-gobject-introspection-when-cross-comp.patch \
           "
SRC_URI[archive.md5sum] = "ff31e7d0594df44318e12facda3d086e"
SRC_URI[archive.sha256sum] = "fd55a9037d39e7a10f0db64309f5f0265fa32ec962bf85066087b83a2807f40a"

GNOMEBASEBUILDCLASS = "meson"
inherit gnomebase lib_package gobject-introspection gtk-doc manpages gettext

GTKDOC_ENABLE_FLAG = "-Denable-gtk-doc=true"
GTKDOC_DISABLE_FLAG = "-Denable-gtk-doc=false"

GI_ENABLE_FLAG = "-Ddisable_introspection=false"
GI_DISABLE_FLAG = "-Ddisable_introspection=true"

EXTRA_OEMESON_append_class-target = " ${@bb.utils.contains('GTKDOC_ENABLED', 'True', '${GTKDOC_ENABLE_FLAG}', \
                                                                                    '${GTKDOC_DISABLE_FLAG}', d)} "
EXTRA_OEMESON_append_class-target = " ${@bb.utils.contains('GI_DATA_ENABLED', 'True', '${GI_ENABLE_FLAG}', \
                                                                                    '${GI_DISABLE_FLAG}', d)} "

PACKAGECONFIG[manpages] = "-Denable-man=true, -Denable-man=false, libxslt-native xmlto-native"

do_install_append() {
    # FIXME: these need to be provided via ptest
    rm -rf ${D}${datadir}/installed-tests ${D}${libexecdir}
}

BBCLASSEXTEND = "native nativesdk"