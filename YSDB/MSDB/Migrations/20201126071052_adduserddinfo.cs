using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class adduserddinfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "dingUserId",
                table: "ys_user",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "unionid",
                table: "ys_user",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AlterColumn<string>(
                name: "contentUrl",
                table: "policyContent",
                maxLength: 500,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 300);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "dingUserId",
                table: "ys_user");

            migrationBuilder.DropColumn(
                name: "unionid",
                table: "ys_user");

            migrationBuilder.AlterColumn<string>(
                name: "contentUrl",
                table: "policyContent",
                maxLength: 300,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 500);
        }
    }
}
