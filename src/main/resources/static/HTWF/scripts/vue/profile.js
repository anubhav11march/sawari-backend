Vue.component('profile', {
    data: function (){
        return{
            userInfo: [],
            validationMessage: [],
        }
    },
    mounted(){
      axios.get('/author/profile')
          .then(response => {
              this.userInfo = response.data;
          })
          .catch(error =>{
              console.log(error);
          })
    },
    watch: {
        email(value) {
            this.email = value
            this.validateEmail(value)
        },
        password(value) {
            this.password = value
            this.validatePassword(value)
        },
        phone(value) {
            this.phone = value
            this.validatePhone(value)
        }
    },
    methods: {
        validateEmail(value) {
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
                this.validationMessage.email = ''
            } else {
                this.validationMessage.email = `${i18next.t('invalidEmail')}`
            }
        },
        validatePassword(value) {
            let difference = 8 - value.length;
            if (value.length < 8) {
                this.validationMessage.password = 'Must be 8 characters! '+ difference + ' characters left' ;
            } else {
                this.validationMessage.password = '';
            }
        },
        validatePhone(value) {
            if (/^\d{10}$/.test(value)) {
                this.validationMessage.phone = ''
            } else {
                this.validationMessage.phone = `${i18next.t('invalidPhone')}`
            }
        },
        updateUserInfo(){
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            var self = this;
            const request = {
                method: 'PUT',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: this.userInfo,
                url: '/author/profile/update',
            }
            console.log("userInfo: ", request.data)
            axios(request)
                .then(response => {
                    swal("Success!", `${i18next.t('profileUpdateMessage')}`, "success");
                })
                .catch(error => {
                    swal("Error!", error.response.data.message, "error");
                })
        },
    },
    template:
        `<div class="container">
            <div class="row gutters">
                <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                    <div class="card ">
                        <div class="card-body">
                        <form >
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <h4 class="mb-2 text-primary font-weight-bold">${i18next.t('personalDetails')}</h4>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('firstName')}</label>
                                        <input type="text" class="form-control" id="fullName" v-model="userInfo.firstName" placeholder="${i18next.t('enterFirstName')}">
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('middleName')}</label>
                                        <input type="text" class="form-control" id="middleName" v-model="userInfo.middleName" placeholder="${i18next.t('enterMiddleName')}">
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('lastName')}</label>
                                        <input type="text" class="form-control" id="lastName" v-model="userInfo.lastName" placeholder="${i18next.t('enterLastName')}">
                                    </div>
                                </div>
                                
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('phone')}</label>
                                        <input type="text" class="form-control" id="phone" v-model="userInfo.phone" placeholder="${i18next.t('enterPhone')}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('mobile')}</label>
                                        <input type="text" class="form-control" id="phone" v-model="userInfo.mobile" placeholder="${i18next.t('enterMobile')}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('primaryEmail')}</label>
                                        <input type="email" class="form-control" disabled id="email" v-model="userInfo.email" placeholder="${i18next.t('enterPrimaryEmail')}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('secondaryEmail')}</label>
                                        <input type="email" class="form-control"  id="email" v-model="userInfo.alternateEmail" placeholder="${i18next.t('enterSecondaryEmail')}">
                                    </div>
                                </div>
                                
                            </div>
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <h4 class="mt-3 mb-2 text-primary font-weight-bold">${i18next.t('address')}</h4>
                                </div>
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <div class="form-group">
                                        <label for="Street">${i18next.t('address')}</label>
                                        <input type="name" class="form-control" id="address" v-model="userInfo.street" placeholder="${i18next.t('enterAddress')}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="ciTy">${i18next.t('city')}</label>
                                        <input type="name" class="form-control" id="ciTy" v-model="userInfo.city" placeholder="Enter City">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="sTate">${i18next.t('state')}</label>
                                        <input type="text" class="form-control" id="sTate" v-model="userInfo.state" placeholder="Enter State">
                                    </div>
                                </div>
                                 <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="sTate">${i18next.t('country')}</label>
                                        <input type="text" class="form-control" id="sTate" v-model="userInfo.country" placeholder="Enter Country">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="zIp">${i18next.t('postalCode')}</label>
                                        <input type="text" class="form-control" id="zIp" v-model="userInfo.zipCode" placeholder="${i18next.t('postalCode')}">
                                    </div>
                                </div>
                            </div>
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <h4 class="mt-3 mb-2 text-primary font-weight-bold">${ i18next.t('education')}</h4>
                                </div>
                               
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="ciTy">${i18next.t('institution')}</label>
                                        <input type="name" class="form-control" id="ciTy" v-model="userInfo.institution" placeholder="${i18next.t('enterInstitution')}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label for="sTate">${i18next.t('department')}</label>
                                        <input type="text" class="form-control" id="sTate" v-model="userInfo.department" placeholder="${i18next.t('enterDepartment')}">
                                    </div>
                                </div>
                                
                            </div>
                        <div class="row gutters">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="text-right">
                                    <a href="/home"><button type="button" id="submit" name="submit" class="btn btn-secondary">${i18next.t('cancel')}</button></a>
                                    <button type="submit" @click.prevent="updateUserInfo" id="submit" name="submit" class="btn btn-primary">${i18next.t('update')}</button>
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
new Vue({ el: "#profile"})