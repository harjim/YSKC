using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterPdocFileDataIdToPdocFileId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "pdocFileDataId",
                table: "p_docFile_trial",
                newName: "pdocFileId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "pdocFileId",
                table: "p_docFile_trial",
                newName: "pdocFileDataId");
        }
    }
}
