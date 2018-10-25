<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Concerts</h2>
  <table border="0">
    <tr>
      <th style="text-align:left">Name</th>
      <th style="text-align:left">Genre</th>
      <th style="text-align:left">Place</th>
    </tr>
    <xsl:for-each select="booklet/concert">
        <xsl:sort select="band_name" />
        <xsl:if test="(tickets/ticket/price &lt; 100) and (venue/address='Mangalore') and (details/date/month='April')">
        <xsl:choose>
        <xsl:when test="tickets/ticket/discount &gt; 0">
            <tr  bgcolor="yellow">
            <td><xsl:value-of select="band_name"/></td>
            <td><xsl:value-of select="genre"/></td>
            <td><xsl:value-of select="venue/name"/></td>
            </tr>
        </xsl:when>
        <xsl:otherwise>
            <tr>
            <td><xsl:value-of select="band_name"/></td>
            <td><xsl:value-of select="genre"/></td>
            <td><xsl:value-of select="venue/name"/></td>
            </tr>
        </xsl:otherwise>
        </xsl:choose>
        
        </xsl:if>
        <!-- </xsl:sort> -->
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>