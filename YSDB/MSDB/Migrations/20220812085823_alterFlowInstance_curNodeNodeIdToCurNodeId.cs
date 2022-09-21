using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterFlowInstance_curNodeNodeIdToCurNodeId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "nodeId",
                table: "flowInstance_curNode",
                newName: "curNodeId");

            migrationBuilder.RenameIndex(
                name: "IX_flowInstance_curNode_instanceId_nodeId",
                table: "flowInstance_curNode",
                newName: "IX_flowInstance_curNode_instanceId_curNodeId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "curNodeId",
                table: "flowInstance_curNode",
                newName: "nodeId");

            migrationBuilder.RenameIndex(
                name: "IX_flowInstance_curNode_instanceId_curNodeId",
                table: "flowInstance_curNode",
                newName: "IX_flowInstance_curNode_instanceId_nodeId");
        }
    }
}
