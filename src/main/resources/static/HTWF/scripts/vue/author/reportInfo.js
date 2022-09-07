Vue.component('reportInfo',{
    data: function(){
        return {
            reportInfo: {},
            services: [],
            selectedServices: [],
            isNotPriceZero: true,
            buttonText: {true: `${i18next.t('checkout')}`, false: `${i18next.t('getQuote')}`},
            remark: '',
            doesOrderExist: false,
            orderDetail: {},
        }
    },
    mounted(){
        let urlParams = new URLSearchParams(window.location.search);
        var fileId = urlParams.get('fileId')
        this.getReportInfo(urlParams.get('reportId'),fileId)
        this.getOrderDetail(fileId)
    },
    methods:{
        getReportInfo(reportId,fileId,authorId){
            axios.get(`/author/content/report?fileId=${fileId}&reportId=${reportId}`)
                .then(response =>{
                    this.reportInfo = response.data
                    this.getServicesAndPrices(this.splitProposedServices(this.reportInfo.proposedServices))
                    this.selectedServices = this.splitProposedServices(response.data.proposedServices)
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        downloadReport(reportId,fileId){
            window.location.href = `/author/content/download?isReport=Y&reportId=${reportId}&fileId=${fileId}`;
        },
        splitProposedServices(commaSeparatedServices){
            return commaSeparatedServices.split(",")
        },
        getServicesAndPrices(proposedServices) {
            axios.get("/author/plans")
                .then(response => {
                    this.services = response.data
                        .map(service => ({name: service.name, price: service.price, currency: service.currency}))
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getOrderDetail(fileId){
            axios.get(`/author/file-order?fileId=${fileId}`)
                .then(response => {
                    this.orderDetail = response.data
                    this.doesOrderExist = ['PAID','APPROVED'].includes(this.orderDetail.status)
                })
                .catch(error => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        createOrder(){
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            var data = {
                fileId: this.reportInfo.authorFileId,
                reportId: this.reportInfo.adminFileId,
                authorId: this.reportInfo.authorUserId,
                adminId: this.reportInfo.fileUserId,
                selectedServices: this.selectedServices.join(","),
                estimatedAmount: this.getTotalPriceAndCheckForZero(this.selectedServices),
                remark: this.remark,
            }
            const request = {
                method: 'POST',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: data,
                url: '/author/order',
            }
            axios(request)
                .then(response =>{
                    window.location.href = "/author/payment";
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getTotalPriceAndCheckForZero(selectedServices){
            var prices = this.services.filter(service => (selectedServices.includes(service.name)))
                .map(service => (service.price))
            this.isNotPriceZero = !prices.includes(0)
            return prices.reduce((a,b) => a+b,0)
        },
        gotoOrder(orderId){
            window.location.href = `/author/orderdetail?orderId=${orderId}`
        },
    },
    template:
        `<div>
            <div class="container content">
                        <button @click="downloadReport(reportInfo.adminFileId,reportInfo.authorFileId)" class="btn btn-sm" type="button" style="color: white">
                        <i class="fa fa-file-image-o"></i>${i18next.t('downloadReport')}
                    </button>
                    <button v-if="doesOrderExist" @click="gotoOrder(orderDetail.orderId)" class="btn btn-sm" type="button" style=" background-color: #4CAF50; color: white">
                        ${i18next.t('order')} #{{ orderDetail.orderId }}
                    </button>
                        <hr class="space">
                        <div class="container">
                        <div class="row gutters">
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card ">
                                    <div class="card-body">
                                        <form action="" method="post">
                                            <div class="row gutters">
                                                <input type="hidden" id="authorFileId" name="authorFileId" value="">
                                                <input type="hidden" id="authorUserId" name="authorUserId" value="">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="topic">${i18next.t('topic')}</label>
                                                        <input type="text" class="form-control" id="topic" v-model="reportInfo.topic" disabled name="topic">
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="category">${i18next.t('category')}</label>
                                                        <input type="text" class="form-control" id="category" v-model="reportInfo.category" disabled name="category">
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="language">${i18next.t('language')}</label>
                                                        <input type="text" class="form-control" id="language" v-model="reportInfo.language" disabled name="language">
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="reportType">${i18next.t('reportType')}</label>
                                                        <input type="text" class="form-control" id="reportType" v-model="reportInfo.reportType" disabled name="reportType">
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="description">${i18next.t('description')}</label>
                                                        <textarea name="description" id="description" disabled v-model="reportInfo.description"
                                                                  class="form-control"></textarea>
                                                    </div>
                                                </div>
                                                <!--<div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="services">${i18next.t('proposedServices')}</label>
                                                        <input type="text" class="form-control" id="proposedServices" v-model="reportInfo.proposedServices" disabled name="proposedServices">
                                                    </div>
                                                </div>-->
                                            </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <hr >
                    <span v-if="!doesOrderExist">
                    <div class="col-xl-12 col-lg-12 col-md-8 col-sm-8 col-12">
                        <div class="form-group">
                            <h4 style="font-weight: bold">${i18next.t('selectServices')}</h4><br>
                            <span v-for="service in services">
                                <article class="feature1">
                                    <input type="checkbox"  v-model="selectedServices" :id="service.name" :value="service.name"  />
                                    <div>
                                      <span>
                                        {{service.name}}<br/>
                                        <span v-show="service.price != 0">+ {{ service.currency }} {{ service.price }}</span>
                                      </span>
                                    </div>
                                </article>
                            </span>
                        </div>
                    </div>
                    <hr class="space m">
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <div class="form-group">
                                <textarea name="remark" id="remark" v-model="remark" class="form-control" placeholder="Remark"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <div class="text-right">
                                <span v-show="isNotPriceZero">
                                    <label style=" font-size: 16px;  font-weight: bold" >${i18next.t('estimatedAmount')}:&nbsp;&nbsp; </label>
                                    <label style="font-size: 18px; color: red; font-weight: bold;">{{ services[0]?.currency }} {{ getTotalPriceAndCheckForZero(selectedServices) }}</label>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <div class="text-right">
                                <button type="submit" id="submit" @click.prevent="createOrder" style=" font-size: 16px; background-color: #6f5499; color: #fff; font-weight: bold" name="submit" class="btn ">{{ buttonText[isNotPriceZero] }}</button>
                            </div>
                        </div>
                    </div>
                    </span>
                </form>
               
          
        </div>`
})

new Vue({ el:'#report-info' })