Vue.component('login', {
    data: function() {
        return {
            username: '',
            password: '',
        }
    },
    methods: {
        loginUser() {
            console.log("testing");
            console.log('username',this.username);
            console.log('password',this.password);
            axios.post('/login-process',{
                username: this.username,
                password: this.password,
            }).then(response =>{
                console.log(response);
            }).catch(error =>{
                console.log(error);
            })
        }
    },
    template:
        `<div>
            <form>
                <div class="input-group form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                    </div>
                    <input type="text" class="form-control" v-model="username" placeholder="Username">
                    
                </div>
                <div class="input-group form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fas fa-key"></i></span>
                    </div>
                    <input type="password" class="form-control" v-model="password" placeholder="Password">
                </div>
                <div class="row align-items-center remember">
                    <input type="checkbox">Remember Me
                </div>
                <div class="form-group">
                    <input type="submit" @click.prevent="loginUser" value="Login" class="btn float-right login_btn">
                </div>
            </form>
        </div>`
})

new Vue({ el: "#login" })