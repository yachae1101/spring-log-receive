package com.bh.logreceive;

public class CommonUtils {
    private static final String EXETENSION_SEPERATOR = ".";
    private static final String TIME_SEPERATOR = "_";

    //원본 파일을 가지고 실제 업로드되는 파일 이름을 만들어주는 메서드
    public static String fileNameCreate(String fileName){
        int extensionIndex = fileName.lastIndexOf(EXETENSION_SEPERATOR);
        String fileExtension = fileName.substring(extensionIndex);
        String uploadName = fileName.substring(0, extensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder(uploadName)
                .append(TIME_SEPERATOR)
                .append(now)
                .append(fileExtension);
        return sb.toString();
    }
}
