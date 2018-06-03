<cfset request.layout = false>

<cfoutput>
    <!DOCTYPE html>

    <html lang="en">
        <head>
            <meta charset="utf-8">
            <!---
            <meta name="description" content="#rc.MetaData.getMetaDescription()#">
            --->
            <!--
            <meta name="keywords" content="#rc.MetaData.getMetaKeywords()#">
            -->
            <meta name="description" content="#rc.MetaData.getMetaDescription()#">
            <meta name="keywords" content="#rc.MetaData.getMetaKeywords()#">
            <cfif rc.MetaData.hasMetaAuthor()><meta name="author" content="#rc.MetaData.getMetaAuthor()#"></cfif>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <base href="#rc.baseHref#">

            <title>#rc.MetaData.getMetaTitle()#</title>

            <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
            <link rel="stylesheet" href="public/assets/css/core.css?r=#rc.config.revision#">

            <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

            <cfif Len(rc.config.googleanalyticstrackingid)>
                <script type="text/javascript">
                var _gaq = _gaq || [];
                _gaq.push(['_setAccount', '#rc.config.googleAnalyticsTrackingId#']);
                _gaq.push(['_trackPageview']);
                (function() {
                    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
                })();
                </script>
                <script src="public/assets/js/outbound-link-tracking.js"></script>
            </cfif>

            <cfif rc.config.news.enabled><link rel="alternate" type="application/rss+xml" href="#buildURL('news.rss')#"></cfif>
        </head>

        <body id="#getSection()#" class="#getItem()#">
            <cfif rc.config.development>
                <span class="development-mode label label-warning">Development Mode</span>
            </cfif>

            <nav class="navbar navbar-default" role="navigation">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="##navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>

                        <a class="navbar-brand" href="#rc.baseHref#" title="Return to home page"><img src="public/assets/img/global/xindi-logo.png" alt="Xindi logo" /></a>
                    </div>

                    <div id="navbar" class="navbar-collapse collapse">
                        #view("partials/navigation")#

                        <form action="#buildURL('search')#" method="post" class="navbar-form navbar-right" id="search" role="search">
                            <div class="form-group">
                                <input type="text" name="searchterm" id="searchterm" class="form-control input-sm">
                            </div>
                            <input type="submit" name="searchbtn" id="searchbtn" value="Search" class="btn btn-default btn-sm">
                        </form>
                    </div>
                </div>
            </nav>

            <div class="container" role="main">#body#</div>

            <div id="footer" role="contentinfo">
                <div class="container">
                    <p><a href="#buildURL('navigation/map')#">Site Map</a></p>
                </div>
            </div>

            <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
            <script src="public/assets/js/core.js?r=#rc.config.revision#"></script>
        </body>
    </html>
</cfoutput>