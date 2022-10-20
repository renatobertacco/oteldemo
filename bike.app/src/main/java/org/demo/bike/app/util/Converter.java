package org.demo.bike.app.util;

import org.demo.bike.app.dto.SizeDTO;
import org.demo.bike.app.model.Size;

public class Converter {

    public static Long toLong(String v) {
        return v != null ? Long.valueOf(v) : null;
    }

    public static SizeDTO toSizeDTO(String v) {
        return v != null ? SizeDTO.valueOf(v) : null;
    }

    public static Size toSize(SizeDTO v) {
        return v != null ? Size.valueOf(v.name()) : null;
    }
}
