Vue.component('indexContent',{
    data: function(){
        return {
        }
    },
    methods:{

    },
    template:
        `<div>
            <div class="section-bg-image parallax-window" data-natural-height="850" data-natural-width="1980" data-parallax="scroll"
     data-image-src="../images/bg-3.jpg">
    <div class="container content">
        <hr class="space m"/>
        <div class="row">
            <div class="col-md-8" data-anfdima="fade-left" data-timeline="asc" data-time="1500">
                <h1 class="text-xl text-m-sm anima"><span class="text-color">${i18next.t('aesh')}</span></h1>
                <p class="anima text-m text-normal">
                    ${i18next.t('aeshPunch')}
                </p>
            </div>

        </div>
        <hr class="space m"/>
        <div class="row">
            <div class="col-md-4">
            <div style="text-align: justify">
                <p class="anima text-normal">
                    ${i18next.t('aeshContent')}
                </p>
                </div>
                <hr class="space m">
                <a href="/login" class="btn btn-lg"><i class="fa fa-gears"></i> ${i18next.t('getStarted')}</a><span class="space"></span>
                <a href="/init/contact-us" class="btn btn-lg btn-border"><i class="fa fa-folder-open"></i> ${i18next.t('contactUs')}</a>
            </div>
            <div class="col-md-4 pulfl-right">
            </div>
        </div>
        <hr class="space m"/>
    </div>
</div>
<div class="section-empty">
    <div class="container content">
        <div class="flexslider carousel outer-navs"
             data-options="minWidth:200,itemMargin:30,numItems:4,controlNav:false">
            <ul class="slides">
                <li>
                    <div class="advs-box advs-box-top-icon boxed-inverse" data-anima="rotate-20" data-trigger="hover">
                        <i class="fa fa-list icon circle anima"></i>
                        <h3>${i18next.t('step1')}</h3>
                        <p>
                            ${i18next.t('step1Content')}
                        </p>
                    </div>
                </li>
                <li>
                    <div class="advs-box advs-box-top-icon boxed-inverse" data-anima="rotate-20" data-trigger="hover">
                        <i class="fa fa-upload icon circle anima"></i>
                        <h3>${i18next.t('step2')}</h3>
                        <p>
                            ${i18next.t('step2Content')}
                        </p>
                    </div>
                </li>
                <li>
                    <div class="advs-box advs-box-top-icon boxed-inverse" data-anima="rotate-20" data-trigger="hover">
                        <i class="fa fa-tasks icon circle anima"></i>
                        <h3>${i18next.t('step3')}</h3>
                        <p>
                            ${i18next.t('step3Content')}
                        </p>
                    </div>
                </li>
                <li>
                    <div class="advs-box advs-box-top-icon boxed-inverse" data-anima="rotate-20" data-trigger="hover">
                        <i class="fa fa-handshake-o icon circle anima"></i>
                        <h3>${i18next.t('step4')}</h3>
                        <p>
                            ${i18next.t('step4Content')}
                        </p>
                    </div>
                </li>
            </ul>
        </div>
        <hr class="space"/>
        <div class="section-empty">
            <div class="container content">
                <div class="row">
                 <div class="col-md-3 boxed-inverse">
                    <div class="title-base text-left">
                        <hr />
                        <h2>${i18next.t('ourServices')}</h2>
                        
                    </div>
                    <p>
                        ${i18next.t('ourServicesContent')}
                    </p>
                    <hr class="space m" />
                    
                </div>
                    <div class="col-md-9 col-sm-12">
                        <hr class="space visible-sm"/>
                        <div class="row">
                            <div class="col-md-4">
                                <h4 class="text-normal">${i18next.t('englishLanguageEditing')}</h4>
                                <p>
                                    ${i18next.t('englishLanguageService')}
                                </p>
                                <a href="/init/english-language-editing" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                            <div class="col-md-4">
                                <hr class="space m visible-xs"/>
                                <h4 class="text-normal">${i18next.t('scientificEditing')}</h4>
                                <p>
                                    ${i18next.t('scientificEditingService')}
                                </p>
                                <a href="/init/scientific-editing" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                            <div class="col-md-4">
                                <hr class="space m visible-xs"/>
                                <h4 class="text-normal">${i18next.t('academicIllustration')}</h4>
                                <p>
                                    ${i18next.t('academicIllustrationService')}
                                </p>
                                <a href="/init/academic-illustration" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                        </div>
                        <hr class="space m"/>
                        <div class="row">
                            <div class="col-md-4">
                                <h4 class="text-normal">${i18next.t('conferencePosterCreation')}</h4>
                                <p>
                                    ${i18next.t('conferencePosterService')}
                                </p>
                                <a href="/init/conference-poster-creation" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                            <div class="col-md-4">
                                <hr class="space m visible-xs"/>
                                <h4 class="text-normal">${i18next.t('researchNewsStories')}</h4>
                                <p>
                                    ${i18next.t('researchNewsStoryService')}
                                </p>
                                <a href="/init/research-news-stories" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                            <div class="col-md-4">
                                <hr class="space m visible-xs"/>
                                <h4 class="text-normal">${i18next.t('journalRecommendation')}</h4>
                                <p>
                                    ${i18next.t('journalRecommendationService')}
                                </p>
                                <a href="/init/journal-recommendation" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                        </div>

                        <hr class="space m"/>
                        <div class="row">
                            <div class="col-md-4">
                                <h4 class="text-normal">${i18next.t('figureFormatting')}</h4>
                                <p>
                                    ${i18next.t('figureFormattingService')}
                                </p>
                                <a href="/init/figure-formatting" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                            <div class="col-md-4">
                                <hr class="space m visible-xs"/>
                                <h4 class="text-normal">${i18next.t('graphicalAbstract')}</h4>
                                <p>
                                    ${i18next.t('graphicalAbstractService')}
                                </p>
                                <a href="/init/graphical-abstract-service" class="btn-text">${i18next.t('readMore')}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="section-bg-image parallax-window" data-natural-height="650" data-natural-width="1980" data-parallax="scroll"
     data-image-src="../images/bg-2.jpg">
    <div class="container content">
        <div class="row proporzional-row">
            <div class="col-md-4 boxed white middle-content text-left">
                <h2 class="text-l">${i18next.t('whyOurServices')}</h2>
                <div style="text-align: justify"><p>${i18next.t('whyOurService')}</p></div>
            </div>
            <div class="col-md-4 col-sm-6 boxed-inverse boxed-border white middle-content text-left">
                <h2 class="text-l">${i18next.t('confidentiality')}
                </h2>
                <div style="text-align: justify"><p>${i18next.t('confidentialityContent')}</p></div>
            </div>
            <div class="col-md-4 col-sm-6 boxed-inverse boxed-border white middle-content text-left">
                <h2 class="text-l">${i18next.t('qualityGuarantee')}
                </h2>
                <hr class="space xs"/>
                <div style="text-align: justify"><p>${i18next.t('qualityGuaranteeContent')}</p></div>
            </div>
        </div>
    </div>
</div>
<div class="section-bg-color">
    <div class="container content">
        <div class="row">
            <div class="col-md-4">
                <div class="title-base text-left">
                    <hr/>
                    <h2>${i18next.t('ourTestimonials')}</h2>
                    <p>${i18next.t('whatOurClientsThink')}</p>
                </div>
                
            </div>
            <div class="col-md-8">
                <div class="flexslider slider nav-inner nav-right" data-options="controlNav:true,directionNav:false">
                    <ul class="slides">
                        <li>
                            <div class="advs-box niche-box-testimonails-cloud">
                                <p>
                                    ${i18next.t('testimonialContent1')}
                                </p>
                                <div class="name-box">
                                    <i class="fa text-l circle onlycover"
                                       style="background-image:url('../images/users/user-7.jpg')"></i>
                                    <h5 class="subtitle">${i18next.t('testimonialName1')}<span class="subtxt">Google</span></h5>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="advs-box niche-box-testimonails-cloud">
                                <p>
                                    ${i18next.t('testimonialContent2')}
                                </p>
                                <div class="name-box">
                                    <i class="fa text-l circle onlycover"
                                       style="background-image:url('../images/users/user-6.jpg')"></i>
                                    <h5 class="subtitle">${i18next.t('testimonialName2')}<span class="subtxt">Facebook</span></h5>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="advs-box niche-box-testimonails-cloud">
                                <p>
                                    ${i18next.t('testimonialContent3')}
                                </p>
                                <div class="name-box">
                                    <i class="fa text-l circle onlycover"
                                       style="background-image:url('../images/users/user-9.jpg')"></i>
                                    <h5 class="subtitle">${i18next.t('testimonialName3')}<span class="subtxt">Google</span></h5>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="section-empty no-paddings-y">
    <div class="container content">
        <hr class="space"/>
        <div class="row">

            <div class="col-md-6 col-sm-12">
                <div class="title-base text-left">
                    <hr/>
                    <h2>${i18next.t('journalRecommendation')}</h2>
                </div>
                <div style="text-align: justify">
                <p>
                    ${i18next.t('journalRecommendationService')}
                </p>
                </div>
                <hr class="space m"/>
                <hr class="space visible-sm"/>
            </div>
            <div class="col-md-6 col-sm-12">
                <div class="title-base text-left">
                    <hr/>
                    <h2>${i18next.t('conferencePosterCreation')}</h2>
                </div>
                <div style="text-align: justify">
                <p>
                   ${i18next.t('conferencePosterService')}
                </p>
                </div>
                <hr class="space m"/>
                <hr class="space visible-sm"/>
            </div>
            <div class="col-md-4 hidden-sm visible-xs">
            </div>
        </div>
    </div>
</div>
        </div>`
})
new Vue({ el:'#indexContent' })
