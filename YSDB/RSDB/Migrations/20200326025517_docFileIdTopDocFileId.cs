using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class docFileIdTopDocFileId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "docFileId",
                table: "p_docFile_data",
                newName: "pdocFileId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "pdocFileId",
                table: "p_docFile_data",
                newName: "docFileId");
        }
    }
}
