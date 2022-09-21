using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class sys_docList_classify : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "classify",
                table: "sys_docList",
                maxLength: 20,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "rdActivities",
                table: "sys_docList",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "subClassify",
                table: "sys_docList",
                maxLength: 20,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "classify",
                table: "sys_docList");

            migrationBuilder.DropColumn(
                name: "rdActivities",
                table: "sys_docList");

            migrationBuilder.DropColumn(
                name: "subClassify",
                table: "sys_docList");
        }
    }
}
