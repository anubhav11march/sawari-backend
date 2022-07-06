Vue.component('findAuthor', {
    data: function() {
        return {
            authors: [],
            perPage: 10,
            currentPage: 1,
        }
    },
    computed: {
        rows() {
            return this.authors.length
        }
    },
    methods: {
        getCompleteAuthors() {
            axios.get('/admin/authors')
                .then((response) => {
                    this.authors = response.data.map((author) => ({
                        userName: author.userName,
                        name: `${author.firstName} ${author.lastName}`,
                        email: author.email,
                        mobile: author.mobile,
                        action: `/admin/authorinfo?authorId=${author.userId}`,
                    }))
                }).catch((error) => {
                swal("Error!", error.response.data.message, "error");
                })
        },
        getIncompleteAuthors() {
            axios.get('/admin/incomplete/authors')
                .then((response) => {
                    this.authors = response.data.map((author) => ({
                        userName: author.userName,
                        name: `${author.firstName} ${author.lastName}`,
                        email: author.email,
                        mobile: author.mobile,
                        action: `/admin/authorinfo?authorId=${author.userId}`,
                    }))
                }).catch((error) => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
        selectAuthors(event) {
            if (event.target.value === 'completeAuthor') {
                this.getCompleteAuthors()
            } else if (event.target.value === 'incompleteAuthor') {
                this.getIncompleteAuthors()
            }
        },
    },
    template:
        `<div>
            <br>
            <div class="container">
                <select class="form-control" @change="selectAuthors($event)" aria-label="Default select example">
                    <option selected >Select</option>
                    <option value="completeAuthor">Complete Authors</option>
                    <option value="incompleteAuthor">Incomplete Authors</option>
                </select>
                <br><br>
                <div class="overflow-auto">
                    <b-table id="my-table" :items="authors" :per-page="perPage" :current-page="currentPage" small>
                        <template #cell(action)="data">
                            <a :href="data.value"> <button type="button" style="background-color: #6A4D8F" class="btn btn-sm">More Info</button></a>
                        </template>
                    </b-table>
                    <p v-show="authors.length > perPage" class="mt-3">Current Page: {{ currentPage }}</p>

                    <b-pagination v-show="authors.length > perPage"
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

new Vue({ el: "#find-author"})