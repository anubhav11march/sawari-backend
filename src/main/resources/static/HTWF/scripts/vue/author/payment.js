Vue.component('checkout',{
    props: ['orderId'],
    data: function(){
        return {
            orderDetail: {},
            // reportInfo: {},
            selectedServices: [],
            qrCodePath: '',
            transactionId: '',
            amount: '',
            paymentDate: '',
            paymentMode: '',
            screenshot: '',
        }
    },
    mounted(){
        // console.log('order Id',this.orderId)
        this.getOrderDetail(this.orderId)
        this.getQrCode('alipay_qr')
        $('#testing').select2();
    },
    methods:{
        getOrderDetail(orderId){
          axios.get(`/author/order/${orderId}`)
              .then(response =>{
                  this.orderDetail = response.data
                  this.selectedServices = this.splitSelectedServices(response.data.selectedServices)
                  this.orderDetail.estimatedAmount = this.orderDetail.estimatedAmount === 0 ? 'N/A' : this.orderDetail.estimatedAmount
              })
              .catch(error =>{
                  swal("Error!", error.response.data.message, "error");
              })
        },
        getQrCode(qrCodeType){
            axios.get(`/author/qrCode?qrCodeType=${qrCodeType}`)
                .then(response => {
                    this.qrCodePath = response.data
                    this.qrCodePath = this.qrCodePath.toString().replaceAll('\\','/')
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
/*        getQrCodeImage(qrCodeType){
            axios.get(`/author/qrCode?qrCodeType=${qrCodeType}`)
                .then(response => {
                    this.qrCodePath = response.data
                    console.log('path: ',response)
                })
                .catch(error =>{
                    console.log('error',error)
                })
        },*/
        splitSelectedServices(commaSeparatedServices){
            return commaSeparatedServices.split(",")
        },
        insertTransactionDetail(){
            const formData = new FormData();
            formData.append("screenshot", this.screenshot);
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'POST',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: formData,
                url: '/author/checkout',
            }
            axios(request)
                .then(response =>{
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
    },
    template:
        `<div>
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
                                <div class="row">
                                    <div class="card ">
                                        <div class="card-body">
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <h4 class="mb-2 text-primary font-weight-bold" style="font-weight: bold; color: #6f5499;">${i18next.t('orderInfo')}</h4>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('orderDate')}</label>
                                                        <input type="text" class="form-control" v-model="orderDetail.orderDate" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('expiryDate')}</label>
                                                        <input type="text" class="form-control" id="expiryDate"  v-model="orderDetail.expiryDate" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('selectedServices')}</label>
                                                        <select class="form-control" v-model="orderDetail.selectedServices" disabled name="services[]" id="selectedServices" multiple="multiple">
                                                            <option v-for="service in selectedServices" selected  :value="service" >{{ service }}</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('estimatedAmount')}</label>
                                                        <input type="text" class="form-control" id="estimatedAmount"  v-model="orderDetail.estimatedAmount" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('remark')}</label>
                                                        <textarea  class="form-control" id="remark"  v-model="orderDetail.remark" disabled></textarea>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>${i18next.t('status')}</label>
                                                        <input type="text"  class="form-control" id="remark"  v-model="orderDetail.status" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <hr class="space m" />
                                
                            </div>
                            <div v-show="orderDetail.status !== 'PENDING'" class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
                                
                                <div class="row">
                                <div class="d-flex justify-content-center">
                                    <select class="form-control" id="qrcode" name="qrcode" @change="getQrCode($event.target.value)" style="width: 50%;">
                                        <option value="wechat_qr">WeChat</option>
                                        <option value="alipay_qr" selected>AliPay</option>
                                    </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="text-left">
                                        <img :src="qrCodePath" width="300" height="300" style="padding: 20px;">
                                    </div>
                                </div>
                                
                            </div>
                                
                            </div>
                        </div>
                    
                
        </div>`
})

new Vue({ el:'#checkout' })