Vue.component('editOrder',{
    data: function(){
        return {
            orderDetail: {},
            selectedServices: [],
            transactionDetail: {},
            statusPending: false,
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
            axios.get(`/admin/order/${orderId}`)
                .then(response =>{
                    this.orderDetail = response.data
                    this.selectedServices = this.splitSelectedServices(response.data.selectedServices)
                    this.statusPending = this.orderDetail.status === 'PENDING'
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getTransactionDetail(orderId){
            axios.get(`/admin/transaction-detail?id=${orderId}`)
                .then(response =>{
                    this.transactionDetail = response.data[0]
                })
                .catch(error =>{
                    swal("Error!", error.response.data.message, "error");
                })
        },
        updateOrderDetail(status){
            this.orderDetail.status = status
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.orderDetail,
                url: '/admin/order',
            }
                axios(request)
                    .then(response => {
                        this.getOrderDetail(this.orderDetail.orderId)
                        swal("Success!", "Order has been updated successfully!", "success");
                    })
                    .catch(error => {
                        swal("Error!", error.response.data.message, "error");
                    })
        },
        splitSelectedServices(commaSeparatedServices){
            return commaSeparatedServices.split(",")
        },
    },
    template:
        `<div>
            
                <div class="container content">
                    <button v-if="orderDetail.status === 'PAID'" @click="updateOrderDetail('APPROVED')" class="btn btn-sm btn-success" type="button" style="background-color: #5cb85c ;color: white">
                        Approve
                    </button>
                    <button v-if="orderDetail.status === 'PAID'" @click="updateOrderDetail('REJECTED')" class="btn btn-sm btn-danger" type="button" style="background-color: #d9534f;color: white">
                        Reject
                    </button>
                    <button v-if="orderDetail.status === 'PENDING'" @click="updateOrderDetail('PLACED')" class="btn btn-sm btn-success" type="button" style="background-color: #5cb85c ;color: white">
                        Place
                    </button>  
                    <hr class="space">
                    <div class="container">      
                        <div class="row gutters">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
                                <div class="row">
                                    <div class="card ">
                                        <div class="card-body">
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <h4 class="mb-2 text-primary font-weight-bold" style="font-weight: bold; color: #6f5499;">Order Info</h4>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label>Order Date</label>
                                                        <input type="text" class="form-control" v-model="orderDetail.orderDate" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label>Expiry Date</label>
                                                        <input type="text" class="form-control" id="expiryDate"  v-model="orderDetail.expiryDate" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>Selected Services</label>
                                                        <select class="form-control" v-model="orderDetail.selectedServices" disabled name="services[]" id="selectedServices" multiple="multiple">
                                                            <option v-for="service in selectedServices" selected  :value="service" >{{ service }}</option> 
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>Estimated Amount</label>
                                                        <input type="text" class="form-control" id="estimatedAmount"  v-model="orderDetail.estimatedAmount" :disabled="!statusPending" >
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>Remark</label>
                                                        <textarea  class="form-control" id="remark"  v-model="orderDetail.remark" disabled></textarea>
                                                    </div>
                                                </div>
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="form-group">
                                                        <label>Status</label>
                                                        <input type="text"  class="form-control" id="remark"  v-model="orderDetail.status" disabled>
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
                        <div v-show="orderDetail.status !== 'PENDING'" class="row">
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
                                    <label for="transactionId">Transaction Id</label>
                                    <input type="text" class="form-control" id="transactionId" v-model="transactionDetail.transactionId"    name="transactionId">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="amount">Total Amount Paid</label>
                                    <input type="text" class="form-control" id="amount" v-model="transactionDetail.amount"   name="amount">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="paymentDate">Date Of Payment</label>
                                    <input type="date" class="form-control" id="paymentDate" v-model="transactionDetail.paymentDate"  name="paymentDate">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="paymentMode">Payment Mode</label>
                                    <input type="text" class="form-control" id="paymentMode" v-model="transactionDetail.paymentMode"  name="paymentMode">
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label for="screenshot">Snap of Payment Proof</label>
                                    <img :src="transactionDetail.screenshotPath" width="200px" height="200px" >
                                </div>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
            </div>
        </div>
        </div>
        
        </div>`
})

new Vue({ el:'#edit-order' })