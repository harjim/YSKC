using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class rdDeptAddColNodeTypeAndDirection : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "nodeType",
                table: "rdDept",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "textDirection",
                table: "rdDept",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "nodeType",
                table: "rdDept");

            migrationBuilder.DropColumn(
                name: "textDirection",
                table: "rdDept");
        }
    }
}
