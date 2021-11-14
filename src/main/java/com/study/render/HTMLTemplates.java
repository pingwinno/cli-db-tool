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

    public static final String TABLE_ROW_TEMPLATE = """
                <tr>
                  <th>%s</th>
                  <th>%s</th>
                 <th>%s</th>
                </tr>
            """;

    public static final String TABLE_DATA_TEMPLATE = """
                <tr>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                </tr>
            """;

    public static final String BOTTOM = """
            </table>
                        
            </body>
            </html>""";
}
