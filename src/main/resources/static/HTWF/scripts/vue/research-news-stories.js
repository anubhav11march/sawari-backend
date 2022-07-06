Vue.component('researchNewsStories', {
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
                    <h1>${i18next.t('researchNewsStories')}</h1>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="section-empty section-item">
    <div class="container content">
        <div class="row vertical-row">
            <div class="col-md-7">
                <div class="title-base text-left">
                    <hr/>
                </div>
                <div style="text-align: justify">
                <p style="text-align: justify;">
                    ${i18next.t('researchNewsStoriesPageContent')}
                </p>
                </div>
                <hr class="space s"/>
            </div>
        </div>
        <hr class="space"/>
        <hr class="space"/>
    </div>
</div>
            </div>`
})
new Vue({el: '#researchNewsStoriesPage'})
