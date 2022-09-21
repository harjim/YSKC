using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addSys_docListChildTypeAndSys_documentBuildTypeCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "buildType",
                table: "sys_document",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "childType",
                table: "sys_docList",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "buildType",
                table: "sys_document");

            migrationBuilder.DropColumn(
                name: "childType",
                table: "sys_docList");
        }
    }
}
