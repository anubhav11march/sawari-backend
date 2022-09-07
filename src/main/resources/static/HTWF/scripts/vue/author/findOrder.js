Vue.component('findOrder', {
    data: function() {
        return {
            perPage: 10,
            currentPage: 1,
            orders: [],
            orderFields: [],
        }
    },
    mounted(){
        this.getOrders()
        this.orderFields = [
            { key: 'sNo', label: `${i18next.t('sNo')}` },
            { key: 'fileId', label: `${i18next.t('fileId')}` },
            { key: 'reportId', label: `${i18next.t('reportId')}`},
            { key: 'date', label: `${i18next.t('date')}` },
            { key: 'status', label: `${i18next.t('status')}` },
            { key: 'totalAmount', label: `${i18next.t('totalAmount')}` },
            { key: 'action', label: `${i18next.t('action')}` },
        ]
    },
    computed: {
        rows() {
            return this.orders.length
        }
    },
    methods: {
        getOrders() {
            axios.get('/author/orders')
                .then(response => {
                    var count = 1
                    this.orders = response.data.map((order) => ({
                        sNo: count++,
                        fileId: order.fileId,
                        reportId: {fileId: order.fileId, reportId: order.reportId},
                        date: order.orderDate,
                        status: order.status,
                        totalAmount: order.estimatedAmount,
                        action: `/author/orderdetail?orderId=${order.orderId}`,
                    }))
                })
                .catch(error => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        getFileUrl(fileId){
            return `/author/content/editfile?fileId=${fileId}`
        },
        getReportUrl(fileId,reportId){
            return `/author/reportinfo?fileId=${fileId}&reportId=${reportId}`
        },
        formatDate(inputDate) {
            date = new Date(inputDate);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
        },
    },
    template:
        `<div>
            <div class="overflow-auto">
                <b-table id="my-table" :fields="orderFields" :items="orders" :per-page="perPage" :current-page="currentPage" small>
                     <template #cell(fileId)="data">
                        <a :href="getFileUrl(data.value)" style="color: blue; text-decoration: underline;">{{ data.value }}</a>
                    </template>
                    <template #cell(reportId)="data">
                        <a :href="getReportUrl(data.value.fileId, data.value.reportId)" style="color: blue; text-decoration: underline;">{{ data.value.reportId }}</a>
                    </template>
                    <template #cell(action)="data">
                        <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">${i18next.t('moreInfo')}</button></a>
                    </template>
                </b-table>
                <p v-show="orders.length > perPage" class="mt-3">${i18next.t('currentPage')}: {{ currentPage }}</p>

                <b-pagination v-show="orders.length > perPage"
                    v-model="currentPage"
                    :total-rows="rows"
                    :per-page="perPage"
                    aria-controls="my-table"
                    first-text="First"
                    prev-text="Prev"
                    next-text="Next"
                    last-text="Last"
                    class="customPagination">
                </b-pagination>
            </div>
        </div>`
})

new Vue({ el: "#findOrder" })