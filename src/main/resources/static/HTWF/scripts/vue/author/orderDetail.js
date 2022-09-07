Vue.component('orderDetail',{
    data: function(){
        return {
            orderDetail: {},
            selectedServices: [],
            transactionDetail: {},
        }
    },
    mounted(){
        // console.log('order Id',this.orderId)
        let urlParams = new URLSearchParams(window.location.search);
        var orderId = urlParams.get("orderId");
        this.getOrderDetail(orderId)
        this.getTransactionDetail(orderId)
        $('#selectedServices').select2();
    },
    methods:{
        getOrderDetail(orderId){
            axios.get(`/author/order/${orderId}`)
                .then(response =>{
                    this.orderDetail = response.data
                    this.selectedServices = this.splitSelectedServices(response.data.selectedServices)
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getTransactionDetail(orderId){
            axios.get(`/author/transaction-detail?id=${orderId}`)
                .then(response =>{
                    this.transactionDetail = response.data[0]
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        downloadSnap(path){
            var imagePath = path.toString().replaceAll('\\','/')
            window.location.href = `/author/image/download?path=${imagePath}`;
        },
        splitSelectedServices(commaSeparatedServices){
            return commaSeparatedServices.split(",")
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
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>     
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
            <div class="card ">
                <div class="card-body">
                    <form action="" method="post" >
                        <div class="row gutters">
                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                            <h4 class="mb-2 text-primary font-weight-bold" style="font-weight: bold; color: #6f5499;">Transaction Details</h4>
                        </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="transactionId">${i18next.t('transactionId')}</label>
                                    <input type="text" class="form-control" id="transactionId" v-model="transactionDetail.transactionId"    name="transactionId">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="amount">${i18next.t('totalAmountPaid')}</label>
                                    <input type="text" class="form-control" id="amount" v-model="transactionDetail.amount"   name="amount">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="paymentDate">${i18next.t('dateOfPayment')}</label>
                                    <input type="date" class="form-control" id="paymentDate" v-model="transactionDetail.paymentDate"  name="paymentDate">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="paymentMode">${i18next.t('paymentMode')}</label>
                                    <input type="text" class="form-control" id="paymentMode" v-model="transactionDetail.paymentMode"  name="paymentMode">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="screenshot">${i18next.t('uploadSnapOfPaymentProof')}</label>
                                    <button type="submit" id="submit" @click.prevent="downloadSnap(transactionDetail.screenshotPath)" name="submit" class="btn btn-primary">Download Snap</button>
                                </div>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
            </div>
        </div>
        </div>`
})

new Vue({ el:'#orderDetail' })