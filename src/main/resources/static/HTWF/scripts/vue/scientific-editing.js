Vue.component('scientificEditing', {
    data: function () {
        return {}
    },
    methods: {},
    template:
        `<div>
<div class="header-base">
    <div class="container">
        <div class="row">
            <div class="col-md-9">
                <div class="title-base text-left">
                    <h1>${i18next.t('scientificEditing')}</h1>
                </div>
            </div>
        </div>
    </div>
</div>
<!--<div class="section-bg-image parallax-window" data-natural-height="850" data-natural-width="1980" data-parallax="scroll"
     data-image-src="../../images/bg-3.jpg">-->
<div class="section-empty section-item">
    <div class="container content">
        <div class="row vertical-row">
        <div class="col-md-5">
                <img class="zoomimg" src="../../../images/atom-biotechnology-nuclear-medicine-with-scientist-rsquo-s-hands-digital-transformation-remix.jpg">
            </div>
            <div class="col-md-7">
                <div class="title-base text-left">
                    <hr/>
                </div>
                <div style="text-align: justify">
                <p style="text-align: justify;">
                    ${i18next.t('scientificEditingPageContent')}
                </p>
                </div>
                <hr class="space s"/>
            </div>
            
        </div>
        <hr class="space"/>
        <hr class="space"/>
    </div>
</div>
<!--</div>-->
            </div>`
})
new Vue({el: '#scientificEditingPage'})
