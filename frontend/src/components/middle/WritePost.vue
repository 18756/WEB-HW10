<template>
    <div class="post form-box">
        <div class="header">Write post</div>
        <div class="body">
            <form @submit.prevent="onWritePost">
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" v-model="title"/>
                    </div>
                </div>

                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" v-model="text"/>
                    </div>
                </div>

                <div class="error">{{error}}</div>

                <div class="button-field">
                    <input type="submit" value="Write post">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function() {
            return {
                title: "",
                text: "",
                error: ""
            }
        },
        name: "WritePost",
        beforeCreate() {
            this.$root.$on("onWritePostValidationError", (error) => {
                this.error = error;
            });
        },
        beforeMount() {
            this.title = "";
            this.text = "";
            this.error = "";
        }, methods: {
            onWritePost: function () {
                this.$root.$emit("onWritePost", this.title, this.text);
            }
        }
    }
</script>

<style scoped>

</style>
