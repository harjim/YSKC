using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addPDocFileFooterColType : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "approvalId",
                table: "p_docFile_footer");

            migrationBuilder.DropColumn(
                name: "auditorId",
                table: "p_docFile_footer");

            migrationBuilder.DropColumn(
                name: "toCompileId",
                table: "p_docFile_footer");

            migrationBuilder.AddColumn<string>(
                name: "approvalEnumber",
                table: "p_docFile_footer",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "auditorEnumber",
                table: "p_docFile_footer",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "toCompileEnumber",
                table: "p_docFile_footer",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "approvalEnumber",
                table: "p_docFile_footer");

            migrationBuilder.DropColumn(
                name: "auditorEnumber",
                table: "p_docFile_footer");

            migrationBuilder.DropColumn(
                name: "toCompileEnumber",
                table: "p_docFile_footer");

            migrationBuilder.AddColumn<int>(
                name: "approvalId",
                table: "p_docFile_footer",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "auditorId",
                table: "p_docFile_footer",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "toCompileId",
                table: "p_docFile_footer",
                nullable: true);
        }
    }
}
