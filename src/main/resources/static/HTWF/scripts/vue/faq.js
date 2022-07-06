Vue.component('faq', {
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
                        <h1>${i18next.t('faq')}</h1>
                        <p>${i18next.t('faqTag')}</p>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    <div class="section-empty section-item">
        <div class="container content">
            <div class="row">
                <div class="col-md-8">
                    <div id="general" class="title-base text-left">
                        <hr />
                        <h2>${i18next.t('generalQuestions')}</h2>
                       
                    </div>
                    <div class="list-group accordion-list" data-time="1000" data-type='accordion'>
                        <div class="list-group-item">
                            <a>  ${i18next.t('q1')} </a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans1')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q2')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans2')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q3')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans3')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q4')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans4')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a>  ${i18next.t('q5')} </a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans5')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q6')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans6')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q7')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans7')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q8')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans8')}
                                </div>
                            </div>
                        </div>
                        <div class="list-group-item">
                            <a> ${i18next.t('q9')}</a>
                            <div class="panel">
                                <div class="inner">
                                    ${i18next.t('ans9')}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
            </div>`
})
new Vue({el: '#faqPage'})
