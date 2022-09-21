using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class changeColAuditorToAudit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "auditorEnumber",
                table: "p_docFile_footer",
                newName: "auditEnumber");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "auditEnumber",
                table: "p_docFile_footer",
                newName: "auditorEnumber");
        }
    }
}
