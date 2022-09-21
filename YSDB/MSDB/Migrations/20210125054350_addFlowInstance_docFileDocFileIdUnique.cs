using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_docFileDocFileIdUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_docFile_docFileId",
                table: "flowInstance_docFile",
                column: "docFileId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_docFile_docFileId",
                table: "flowInstance_docFile");
        }
    }
}
