package com.study.render;

public class HTMLTemplates {

    public static final String HEADER = """
            <!DOCTYPE html>
            <html>
            <style>
            table, th, td {
              border:1px solid black;
            }
            </style>
            <body>
                        
                        
            <table style="width:100%">
            """;

    public static final String TABLE_ROW_START =
            """
                <tr>
            """;
    public static final String TABLE_ROW_END =
            """
                </tr>
            """;
    public static final String TABLE_DATA_TEMPLATE =
            """    
                            <td>%s</td>
                    """;

    public static final String TABLE_HEADER_TEMPLATE =
            """                    
                            <th>%s</th>
                    """;

    public static final String BOTTOM = """
            </table>
                        
            </body>
            </html>""";

    public static final String LINE_SEPARATOR = "\n\r";
}
