using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class user_dept_unionid_dingUserId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "dingUserId",
                table: "user_dept",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<bool>(
                name: "isAdmin",
                table: "user_dept",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "unionid",
                table: "user_dept",
                maxLength: 100,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "dingUserId",
                table: "user_dept");

            migrationBuilder.DropColumn(
                name: "isAdmin",
                table: "user_dept");

            migrationBuilder.DropColumn(
                name: "unionid",
                table: "user_dept");
        }
    }
}
