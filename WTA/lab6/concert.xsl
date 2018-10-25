<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Concerts</h2>
  <table border="1">
    <tr>
      <th style="text-align:left">Name</th>
      <th style="text-align:left">Genre</th>
      <th style="text-align:left">Place</th>
    </tr>
    <xsl:for-each select="booklet/concert">
    <tr>
      <td><xsl:value-of select="band_name"/></td>
      <xsl:choose>
        <xsl:when test="genre='Rock'">
          <td bgcolor="#ff0000"><xsl:value-of select="genre"/></td>
        </xsl:when>
        <xsl:when test="genre='Electronic'">
          <td bgcolor="#7777ff"><xsl:value-of select="genre"/></td>
        </xsl:when>
        <xsl:otherwise>
          <td><xsl:value-of select="genre"/></td>
        </xsl:otherwise>
      </xsl:choose>
      <td><xsl:value-of select="venue/name"/></td>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>