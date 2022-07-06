Vue.component('findOrder', {
    data: function() {
        return {
            orders: [],
            perPage: 10,
            currentPage: 1,
        }
    },
    computed: {
        rows() {
            return this.orders.length
        }
    },
    methods: {
        getPendingOrders() {
            axios.get('/admin/pending-orders')
                .then((response) => {
                    this.orders = response.data.map((order) => ({
                        fileId: {fileId: order.fileId, authorId: order.authorId},
                        reportId: {fileId: order.fileId, reportId: order.reportId, authorId: order.authorId},
                        date: this.formatDate(order.orderDate),
                        status: order.status,
                        expiryDate: this.formatDate(order.expiryDate),
                        action: `/admin/editorder?orderId=${order.orderId}`,
                    }))
                }).catch((error) => {
                swal("Error!", error.response.data.message, "error");
            })
        },
        getPaidOrders() {
            axios.get('/admin/paid-orders')
                .then((response) => {
                    this.orders = response.data.map((order) => ({
                        fileId: {fileId: order.fileId, authorId: order.authorId},
                        reportId: {fileId: order.fileId, reportId: order.reportId, authorId: order.authorId},
                        date: this.formatDate(order.orderDate),
                        status: order.status,
                        expiryDate: this.formatDate(order.expiryDate),
                        totalAmount: order.estimatedAmount,
                        action: `/admin/editorder?orderId=${order.orderId}`,
                    }))
                }).catch((error) => {
                swal("Error!", error.response.data.message, "error");
            })
        },
        selectOrders(event) {
            if (event.target.value === 'paidOrders') {
                this.getPaidOrders()
            } else if (event.target.value === 'pendingOrders') {
                this.getPendingOrders()
            }
        },
        getFileUrl(authorId, fileId){
            return `/admin/editfile?authorId=${authorId}&fileId=${fileId}`
        },
        getReportUrl(authorId, fileId,reportId){
            return `/admin/editreport?authorId=${authorId}&fileId=${fileId}&reportId=${reportId}`
        },
        formatDate(inputDate) {
            date = new Date(inputDate);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
        },
    },
    template:
        `<div>
            <br>
            <div class="container">
                <select class="form-control" @change="selectOrders($event)" aria-label="Default select example">
                    <option selected >Select</option>
                    <option value="paidOrders">Paid Orders</option>
                    <option value="pendingOrders">Pending Orders</option>
                </select>
                <br><br>
                <div class="overflow-auto">
                    <b-table id="my-table" :items="orders" :per-page="perPage" :current-page="currentPage" small>
                        <template #cell(fileId)="data">
                            <a :href="getFileUrl(data.value.authorId, data.value.fileId)" style="color: blue; text-decoration: underline;">{{ data.value.fileId }}</a>
                        </template>
                        <template #cell(reportId)="data">
                            <a :href="getReportUrl(data.value.authorId, data.value.fileId, data.value.reportId)" style="color: blue; text-decoration: underline;">{{ data.value.reportId }}</a>
                        </template>
                        <template #cell(action)="data">
                            <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">More Info</button></a>
                        </template>
                    </b-table>
                    <p v-show="orders.length > perPage" class="mt-3">Current Page: {{ currentPage }}</p>

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
            </div>
        </div>`
})

new Vue({ el: "#find-order"})