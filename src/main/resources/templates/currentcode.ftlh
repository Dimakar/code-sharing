<html>
<head>
    <title>Code</title>
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#if date??>
    <span id="load_date">${date}</span>
</#if>
<#if views??>
    <div>
        <span id="views_restriction">${views}</span>
        <span> more views allowed</span>
    </div>
</#if>
<#if time??>
    <div>
        <span>The code will be available for</span>
        <span id="time_restriction">${time}</span>
        <span> seconds</span>
    </div>
</#if>
<#if code??>
<pre id="code_snippet"><code>${code}</code></pre>
</#if>
</body>
</html>