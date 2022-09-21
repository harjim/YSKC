using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class addUser_infoDeptNameCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "deptName",
                table: "user_info",
                maxLength: 50,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "deptName",
                table: "user_info");
        }
    }
}
